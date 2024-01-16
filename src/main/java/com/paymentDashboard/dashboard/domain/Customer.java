package com.paymentDashboard.dashboard.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _id;
    private String userName;
    private String email;
    private String phoneNo;
    private Long totalAmount;
    private List<String> paymentHistory;


    public Customer(Long _id,String userName, String email, String phoneNo,Long totalAmount, List<String> paymentHistory) {
        this._id= _id;
        this.userName = userName;
        this.email = email;
        this.totalAmount=totalAmount;
        this.phoneNo = phoneNo;
        this.paymentHistory = paymentHistory;
    }

    public Customer() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<String> getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(List<String> paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "_id=" + _id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", totalAmount=" + totalAmount +
                ", paymentHistory=" + paymentHistory +
                '}';
    }
}
