package vn.microservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.microservice.controller.request.PlaceOrderRequest;
import vn.microservice.model.Order;
import vn.microservice.service.OrderService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Validated
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j(topic = "ORDER-CONTROLLER")
public class OrderController {
    private final OrderService orderService;

    /**
     * Get all order
     * @return List order
     */
    @GetMapping("/list")
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        log.info("placeOrder request: {}", request);
        return ResponseEntity.ok(orderService.addOrder(request));
    }

    @PostMapping(path = "/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> generateQRCodeImage(@RequestParam String qrCode) throws Exception {
        BufferedImage image = orderService.generateQRCodeImage(qrCode);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
    }

    @PostMapping(path = "/bar-code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> generateBarcode(@RequestParam String barcode) throws Exception {
        log.info("generateBarcode request: {}", barcode);
        BufferedImage image = orderService.generateBarCodeImage(barcode);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
    }

    @PostMapping("/checkout/{orderId}")
    public ResponseEntity<String> checkoutOrder(@PathVariable String orderId) {
        log.info("checkoutOrder request: {}", orderId);
        return ResponseEntity.ok(orderService.checkoutOrder(orderId));
    }
}
