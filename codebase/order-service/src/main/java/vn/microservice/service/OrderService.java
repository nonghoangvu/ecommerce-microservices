package vn.microservice.service;

import com.google.zxing.WriterException;
import vn.microservice.controller.request.PlaceOrderRequest;

import java.awt.image.BufferedImage;

public interface OrderService {

    String addOrder(PlaceOrderRequest orderRequest);

    BufferedImage generateQRCodeImage(String qrcode) throws WriterException;

    BufferedImage generateBarCodeImage(String barCode) throws WriterException;

    String checkoutOrder(String orderId);
}
