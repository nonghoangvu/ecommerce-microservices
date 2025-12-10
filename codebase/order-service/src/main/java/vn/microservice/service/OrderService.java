package vn.microservice.service;

import com.google.zxing.WriterException;
import vn.microservice.controller.request.PlaceOrderRequest;
import vn.microservice.model.Order;

import java.awt.image.BufferedImage;
import java.util.List;

public interface OrderService {

    List<Order> getAll();

    String addOrder(PlaceOrderRequest orderRequest);

    BufferedImage generateQRCodeImage(String qrcode) throws WriterException;

    BufferedImage generateBarCodeImage(String barCode) throws WriterException;

    String checkoutOrder(String orderId);
}
