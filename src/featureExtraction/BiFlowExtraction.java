package featureExtraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import preprocessFiles.preprocessAs.FormatAsArff;

public class BiFlowExtraction extends FeatureExtraction{
   private static final String BI_FLOW_PATH="Bi flow output/";

   @Override
   public String getName() {
      return "BiFlow";
   }

   @Override
   public String getNormalPath() {
      return BI_FLOW_PATH+"normal.arff";
   }

   @Override
   public String getSlowBodyPath() {
      return BI_FLOW_PATH+"slowBody.arff";
   }

   @Override
   public String getSlowHeadersPath() {
      return BI_FLOW_PATH+"slowHeaders.arff";
   }

   @Override
   public String getSlowReadPath() {
      return BI_FLOW_PATH+"slowRead.arff";
   }

   @Override
   public String getTCPFloodPath() {
      return BI_FLOW_PATH+"tcpFlood.arff";
   }

   @Override
   public String getUDPFloodPath() {
      return BI_FLOW_PATH+"udpFlood.arff";
   }

   @Override
   public String getHTTPFloodPath() {
      return BI_FLOW_PATH+"httpFlood.arff";
   }

   @Override
   public String getNoiseNormalPath() {
      return BI_FLOW_PATH+"Noise normal.arff";
   }

   @Override
   public String getNoiseSlowBodyPath() {
      return BI_FLOW_PATH+"Noise slowBody.arff";

   }

   @Override
   public String getNoiseSlowHeadersPath() {
      return BI_FLOW_PATH+"Noise slowHeaders.arff";

   }

   @Override
   public String getNoiseSlowReadPath() {
      return BI_FLOW_PATH+"Noise slowRead.arff";

   }

   @Override
   public String getNoiseTCPFloodPath() {
      return BI_FLOW_PATH+"Noise tcpFlood.arff";

   }

   @Override
   public String getNoiseUDPFloodPath() {
      return BI_FLOW_PATH+"Noise udpFlood.arff";

   }

   @Override
   public String getNoiseHTTPFloodPath() {
      return BI_FLOW_PATH+"Noise httpFlood.arff";

   }

   @Override
   public String getKDDTrainPath() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public List<String> getFeaturesToRemove() {
      ArrayList<String> al = new ArrayList<>();
      al.add("src_ip");
      al.add("dst_ip");
      al.add("proto_number");
      al.add("src_port");
      al.add("dst_port");
//      al.add("cof");
      return Collections.unmodifiableList(al);
   }

   @Override
   public Consumer<FormatAsArff> removeNonMatchingClasses() {
      //remove instances that aren't 6 (TCP) or 17 (UDP)
//      return (faa)->faa.removeNonMatchingClasses("proto_number", "6", "17");
      return (faa)->{};
   }
}