package com.rkind.splity.wallet.dto;

public class WalletActivationRequest {

        private Long userId;
        private String email;
        private String walletPin;
        private String deviceId;

        public Long getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public String getWalletPin() {
            return walletPin;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setWalletPin(String walletPin) {
            this.walletPin = walletPin;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
}
