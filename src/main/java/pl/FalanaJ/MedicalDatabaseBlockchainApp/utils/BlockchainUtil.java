package pl.FalanaJ.MedicalDatabaseBlockchainApp.utils;

import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.MedicalHistory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class BlockchainUtil {
    public static String generateHash(String data){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
    public static String prepareMedicalHistoryData(MedicalHistory history, String previousHash) {
        return history.getType() + "|" +
                history.getStatus() + "|" +
                history.getDate() + "|" +
                history.getDescription() + "|" +
                history.getPatient().getId() + "|" +
                (history.getAppointment() != null ? history.getAppointment().getDoctor().getId() : "null") + "|" +
                previousHash;
    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static boolean isChainValid(List<MedicalHistory> historyList){
        String previousHash = "0";
        for (MedicalHistory history : historyList) {
            String calculatedHash = BlockchainUtil.generateHash(BlockchainUtil.prepareMedicalHistoryData(history, previousHash));

            System.out.println("Expected hash: " + history.getHash());
            System.out.println("Calculated hash: " + calculatedHash);
            System.out.println("Previous hash: " + previousHash);
            System.out.println("---");

            if (!calculatedHash.equals(history.getHash())) {
                return false;
            }

            if (!Objects.equals(history.getPreviousHash(), previousHash)) {
                return false;
            }
            previousHash = history.getHash();
        }
        return true;
    }
}
