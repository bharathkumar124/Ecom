package com.example.Resource;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Dto.CommonApiResponse;
import com.example.Dto.ImageDecoder;
import com.example.Dto.ImageUtils;
import com.example.Dto.ProductResponseDto;
import com.example.Service.ProductService;
import com.example.Service.SellerService;
import com.example.entity.Product;
import com.example.entity.Seller;




@Service
public class ProductResource {
	@Autowired
	ProductService ps;
	
	@Autowired
	SellerService ss;
	
	

	public ResponseEntity<CommonApiResponse> addproduct(String name, String description, BigDecimal price, int quantity,
			String status, MultipartFile image1, MultipartFile image2, MultipartFile image3,int sellerid) throws IOException {

		CommonApiResponse api = new CommonApiResponse();
		
		Seller findbyid2 = ss.findbyid(sellerid);
		
		if(findbyid2==null) {
			api.setResponseMessage("seller is not found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		Product p = new Product();
		p.setName(name);
		// p.setCategory(category_id);
		p.setPrice(price);
		p.setQuantity(quantity);
		p.setDescription(description);
		p.setStatus("Active");
		p.setImage1(image1.getBytes());
		p.setImage2(image2.getBytes());
		p.setImage3(image3.getBytes());
		p.setSeller(findbyid2);

		Product add = ps.addpro(p);

		if (add == null) {
			api.setResponseMessage("missing input");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api, HttpStatus.BAD_REQUEST);
		} else
			api.setResponseMessage("product added succcessfully");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api, HttpStatus.OK);

	}

	
	public ResponseEntity<CommonApiResponse> updateproduct(int id,String name, String description, BigDecimal price, int quantity,
			String status, MultipartFile image1, MultipartFile image2, MultipartFile image3) throws IOException{
		CommonApiResponse api=new CommonApiResponse();
		
		Product findbyid = ps.findbyid(id);
		
		if(findbyid==null) {
			api.setResponseMessage("product id not found");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		
		
		//Product p = new Product();
		//p.setId(id);
		findbyid.setName(name);
		// p.setCategory(category_id);
		findbyid.setPrice(price);
		findbyid.setQuantity(quantity);
		findbyid.setDescription(description);
		findbyid.setStatus("Active");
		findbyid.setImage1(image1.getBytes());
		findbyid.setImage2(image2.getBytes());
		findbyid.setImage3(image3.getBytes());
		
		
		Product pp = ps.update(findbyid);
		
		if(pp==null) {
			api.setResponseMessage("failed to update product ");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}else
		api.setResponseMessage("product updated successfully");
		api.setSuccess(true);
		
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
		
	}
	
public ResponseEntity<CommonApiResponse>deletebyid(int id){
		
	String status="Active";
		CommonApiResponse api = new CommonApiResponse();
		
		if(id==0) {
			api.setResponseMessage("id is null");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.BAD_REQUEST);
		}
		Product findbyid = ps.findByIdAndStatus(id, null);

		if(findbyid==null) {
			api.setResponseMessage("product not pfound");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.NOT_FOUND);
		}
		
		
		findbyid.setStatus("Deactive");
		Product update = ps.update(findbyid);
		
		if(update==null) {
			api.setResponseMessage("failed to delete product");
			api.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
			api.setResponseMessage("id not found");
		api.setSuccess(true);
		return new ResponseEntity<CommonApiResponse>(api,HttpStatus.OK);
	}


public ResponseEntity<ProductResponseDto> findalldata() {
    ProductResponseDto dto = new ProductResponseDto();
String status="Active";
    // Make sure ps is not null
    if (ps == null) {
        dto.setResponseMessage("Service not available");
        dto.setSuccess(false);
        return new ResponseEntity<ProductResponseDto>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  List<Product> catt = ps.findByStatus(status);

    if (catt.isEmpty()) {
        dto.setResponseMessage("No data found");
        dto.setSuccess(false);
        return new ResponseEntity<ProductResponseDto>(dto, HttpStatus.NOT_FOUND);
    } else {
        dto.setproducts(catt);
        dto.setResponseMessage("Data fetched successfully");
        dto.setSuccess(true);
        return new ResponseEntity<ProductResponseDto>(dto, HttpStatus.OK);
    }
}




public byte[] getImageDataById(int id) throws IOException, DataFormatException {
    Product product = ps.findbyid(id);
    if (product == null) {
        throw new IOException("Product not found for ID: " + id);
    }

    byte[] imageData1 = product.getImage1();
    byte[] imageData2 = product.getImage2();
    byte[] imageData3 = product.getImage3();
    
    byte[] combinedImageData = concatenateImageBytes(imageData1, imageData2, imageData3);

    if (combinedImageData == null || combinedImageData.length == 0) {
        throw new DataFormatException("Image data is empty or not compressed for product ID: " + id);
    }

    return ImageUtils.decompressImageData(combinedImageData);
}

private byte[] concatenateImageBytes(byte[] imageData1, byte[] imageData2, byte[] imageData3) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         BufferedOutputStream bufferedOutput = new BufferedOutputStream(outputStream)) {

        bufferedOutput.write(imageData1);
        bufferedOutput.write(imageData2);
        bufferedOutput.write(imageData3);

        bufferedOutput.flush(); // Ensure all data is written

        return outputStream.toByteArray();
    } catch (IOException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., log and return null
        return null;
    }
}

public ResponseEntity<ProductResponseDto>findbyseller(int sellerid){
	
	String status="Active";
	ProductResponseDto api = new ProductResponseDto();
	if(sellerid<=0) {
		api.setResponseMessage("please provide correct sellerid");
		api.setSuccess(false);
		return new ResponseEntity<ProductResponseDto>(api,HttpStatus.BAD_REQUEST);
	}
	Seller sell = ss.findByIdAndStatus(sellerid, status);
	
	if(sell==null) {
		api.setResponseMessage("seller not found");
		api.setSuccess(false);
		return new ResponseEntity<ProductResponseDto>(api,HttpStatus.NOT_FOUND);
	}
	List<Product> bySeller = ps.findBySeller(sell);
	
	if(bySeller.isEmpty()) {
		api.setResponseMessage("no products found");
		api.setSuccess(false);
		return new ResponseEntity<ProductResponseDto>(api,HttpStatus.NOT_FOUND);
	}
	
	api.setproducts(bySeller);
	api.setResponseMessage("producrs found");
	api.setSuccess(true);
	return new ResponseEntity<ProductResponseDto>(api,HttpStatus.OK);
}

}
