package vn.microservice.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.microservice.controller.request.PaymentInfoRequest;
import vn.microservice.controller.response.ApiResponse;
import vn.microservice.controller.response.PaymentIntentResponse;
import vn.microservice.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@Slf4j(topic = "PAYMENT-CONTROLLER")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/charge")
    public String charge(@RequestBody Map<String, Object> data) throws StripeException {
        log.info("Charge request received");

        String token = (String) data.get("token");
        double amount = Double.parseDouble(data.get("amount").toString());

        // Call Stripe service to charge the card
        Charge charge = paymentService.charge(token, amount);

        return charge.getStatus(); // Return the payment status
    }

//    @PostMapping("/payment-intent")
//    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
//            throws StripeException {
//
//        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
//        String paymentStr = paymentIntent.toJson();
//
//        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
//    }

    @PostMapping("/payment-intent-2")
    public ResponseEntity<String> createPaymentIntent2(@RequestBody PaymentInfoRequest request)
            throws StripeException {

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency())
//                .setPaymentMethod("card")
                .build();

        // tao payment intent
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // TODO gửi client key để xử lý thành toán
        if (paymentIntent != null) {
            // todo insert database
            return new ResponseEntity<>(paymentIntent.getClientSecret(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token)
            throws Exception {
//        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
//        if (userEmail == null) {
//            throw new Exception("User email is missing");
//        }
        return paymentService.stripePayment("orderId");
    }

    /**
     * curl --location --request POST 'http://localhost:8086/payment/create-payment-intent?amount=100&currency=usd'
     *
     * @param amount
     * @param currency
     * @return
     * @throws StripeException
     */
    @PostMapping("/create-payment-intent")
    public ApiResponse createPaymentIntent(@RequestParam Long amount, @RequestParam String currency) throws StripeException {
        log.info("Create payment intent");

        PaymentIntentResponse response = paymentService.createPaymentIntent(amount, currency);

        return ApiResponse.builder()
                .status(200)
                .message("Payment intent created")
                .data(response)
                .build();
    }
}

