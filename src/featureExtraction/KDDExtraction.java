package featureExtraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import preprocessFiles.preprocessAs.FormatAsArff;

public class KDDExtraction extends FeatureExtraction{
   private static final String KDD_PATH="KDD Feature Extraction/";

   @Override
   public String getName() {
      return "KDD";
   }

   @Override
   public String getNormalPath() {
      return getKDDTrainPath();
   }

   @Override
   public String getSlowBodyPath() {
      return KDD_PATH+"KDD CNIS Slow Body.arff";
   }

   @Override
   public String getSlowHeadersPath() {
      return KDD_PATH+"KDD CNIS Slow Headers.arff";
   }

   @Override
   public String getSlowReadPath() {
      return KDD_PATH+"KDD CNIS Slow Read.arff";
   }

   @Override
   public String getTCPFloodPath() {
      return KDD_PATH+"KDD CNIS TCP Flood.arff";
   }

   @Override
   public String getUDPFloodPath() {
      return KDD_PATH+"KDD CNIS UDP Flood.arff";
   }

   @Override
   public String getHTTPFloodPath() {
      return KDD_PATH+"KDD CNIS HTTP Flood.arff";
   }

   @Override
   public String getKDDTrainPath() {
      return KDD_PATH+"KDDTrain+.arff";
   }

   @Override
   public List<String> getFeaturesToRemove() {
      ArrayList<String> al = new ArrayList<>();
      al.add("service");
      al.add("land");
      al.add("hot");
      al.add("num_failed_logins");
      al.add("logged_in");
      al.add("num_compromised");
      al.add("root_shell");
      al.add("su_attempted");
      al.add("num_root");
      al.add("num_file_creations");
      al.add("num_shells");
      al.add("num_access_files");
      al.add("num_outbound_cmds");
      al.add("is_host_login");
      al.add("is_guest_login");
      al.add("difficulty");
      return Collections.unmodifiableList(al);
   }

   @Override
   public Consumer<FormatAsArff> removeNonMatchingClasses() {
      return (faa)->faa.removeNonMatchingClasses("service", "http", "http_443");
   }
   
   @Override
   public Consumer<FormatAsArff> additionalFormatting() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public String getNoiseNormalPath() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public String getNoiseSlowBodyPath() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public String getNoiseSlowHeadersPath() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public String getNoiseSlowReadPath() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public String getNoiseTCPFloodPath() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public String getNoiseUDPFloodPath() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public String getNoiseHTTPFloodPath() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

}