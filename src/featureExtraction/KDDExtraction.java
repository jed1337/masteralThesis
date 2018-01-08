package featureExtraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KDDExtraction extends FeatureExtraction{
   @Override
   public String getName() {
      return "KDD";
   }

   @Override
   public String getNormalPath() {
      return "KDDTrain+.arff";
   }

   @Override
   public String getSlowBodyPath() {
      return "CNIS Slow Body.arff";
   }

   @Override
   public String getSlowHeadersPath() {
      return "CNIS Slow Headers.arff";
   }

   @Override
   public String getSlowReadPath() {
      return "CNIS Slow Read.arff";
   }

   @Override
   public String getTCPFloodPath() {
      return "CNIS TCP Flood.arff";
   }

   @Override
   public String getUDPFloodPath() {
      return "CNIS UDP Flood.arff";
   }

   @Override
   public String getHTTPFloodPath() {
      return "CNIS HTTP Flood.arff";
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
}