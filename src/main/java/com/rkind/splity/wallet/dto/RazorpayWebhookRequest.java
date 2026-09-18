package com.rkind.splity.wallet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RazorpayWebhookRequest {

    private String event;

    private Payload payload;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payload {

        private Payment payment;

        public Payment getPayment() {
            return payment;
        }

        public void setPayment(Payment payment) {
            this.payment = payment;
        }

    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payment {

        private Entity entity;

        public Entity getEntity() {
            return entity;
        }

        public void setEntity(Entity entity) {
            this.entity = entity;
        }

    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Entity {

        private String id;
        private String order_id;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}