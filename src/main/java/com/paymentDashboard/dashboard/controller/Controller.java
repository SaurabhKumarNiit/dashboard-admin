package com.paymentDashboard.dashboard.controller;
import com.paymentDashboard.dashboard.domain.Booking;
import com.paymentDashboard.dashboard.domain.Customer;
import com.paymentDashboard.dashboard.domain.MyOrder;
import com.paymentDashboard.dashboard.repository.MyOrderRepository;
import com.paymentDashboard.dashboard.services.BookingService;
import com.paymentDashboard.dashboard.services.CustomerServices;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200")
public class Controller {
    @Autowired
    CustomerServices customerService;

    @Autowired
    private MyOrderRepository myOrderRepository;

    private final BookingService bookingService;

    @Autowired
    public Controller(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/userRegister")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        Customer customer1 = customerService.addCustomer(customer);
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }

    @GetMapping("/userData")
    public List<Customer> getAllUserData() {
        return customerService.getCustomer();
    }

    @PutMapping("/update/{_id}")
    public ResponseEntity<?> updateData(@RequestBody Customer customer, @PathVariable Long _id){
        return new ResponseEntity<>(customerService.updateCustomerDetails(customer, _id),HttpStatus.CREATED);
    }


    @PostMapping("/payment/create_order/{email}")
    @ResponseBody
    public String createOrder(@RequestBody Map<String,Object> data, @PathVariable String email){
        System.out.println(data);

        int amount=Integer.parseInt(data.get("amount").toString());

        RazorpayClient client= null;
        try {
            client = new RazorpayClient("rzp_test_LP91fzOg59Pohi","H1ohtNvYEEO8YxskcB02URs1");
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("amount",amount*100);
        jsonObject.put("currency","INR");
        jsonObject.put("receipt","txt_23456");

        Order order = null;
        try {
            order = client.orders.create(jsonObject);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
        System.out.println(order);

        MyOrder myOrder=new MyOrder();

        myOrder.setAmount(order.get("amount")+"");
        myOrder.setOrderId(order.get("id"));
        myOrder.setPaymentId(null);
        myOrder.setStatus("created");
        myOrder.setEmail(email);
        myOrder.setReceipt(order.get("receipt"));
        myOrderRepository.save(myOrder);
//        boolean sendEmail = emailService.sendEmail(email,amount);
        return order.toString();
    }

    @GetMapping("/allHistory")
    public List<MyOrder> getAllPaymentHistory() {
        return myOrderRepository.findAll();
    }

    @PostMapping("/payment/update_order")
    public ResponseEntity<?> updatePayment(@RequestBody Map<String, Object> data) {
        MyOrder myOrder = this.myOrderRepository.findByOrderId(data.get("order_id").toString());

        myOrder.setPaymentId(data.get("payment_id") != null ? data.get("payment_id").toString() : null);

        // Check for null before invoking toString()
        if (data.get("status") != null) {
            myOrder.setStatus(data.get("status").toString());
        } else {
            // Handle the case where the "status" is null, e.g., set a default value
            myOrder.setStatus("paid");
        }

        myOrderRepository.save(myOrder);
        System.out.println(data);
        return ResponseEntity.ok(Map.of("msg", "updated"));
    }

    @GetMapping("/available-time-slots")
    public ResponseEntity<List<String>> getAvailableTimeSlots(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<String> availableTimeSlots = bookingService.getAvailableTimeSlots(date);
        return ResponseEntity.ok(availableTimeSlots);
    }

//    @PostMapping("/book-time-slot")
//    public ResponseEntity<String> bookTimeSlot(@RequestBody BookingRequest bookingRequest) {
//        // You might want to validate the request before proceeding
//        Booking booking = new Booking();
//        booking.setDateTime(bookingRequest.getDateTime());
//
//        bookingService.bookTimeSlot(booking);
//
//        return new ResponseEntity<>("Time slot booked successfully", HttpStatus.CREATED);
//    }

}
