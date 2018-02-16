package featureExtraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import preprocessFiles.preprocessAs.FormatAsArff;

public class NetmateExtraction extends FeatureExtraction{
   private static final String NETMATE_PATH="Netmate/";

   @Override
   public String getName() {
      return "Netmate";
   }

   @Override
   public String getNormalPath() {
      return NETMATE_PATH+"Netmate Normal.arff";
   }

   @Override
   public String getSlowBodyPath() {
      return NETMATE_PATH+"Netmate Slow Body.arff";
   }

   @Override
   public String getSlowHeadersPath() {
      return NETMATE_PATH+"Netmate Slow Headers.arff";
   }

   @Override
   public String getSlowReadPath() {
      return NETMATE_PATH+"Netmate Slow Read.arff";
   }

   @Override
   public String getTCPFloodPath() {
      return NETMATE_PATH+"Netmate TCP Flood.arff";
   }

   @Override
   public String getUDPFloodPath() {
      return NETMATE_PATH+"Netmate UDP Flood.arff";
   }

   @Override
   public String getHTTPFloodPath() {
      return NETMATE_PATH+"Netmate HTTP Flood.arff";
   }

   @Override
   public String getKDDTrainPath() throws UnsupportedOperationException{
      throw new UnsupportedOperationException("Get KDDTrainPath isn't supprted");
   }

   @Override
   public List<String> getFeaturesToRemove() {
      ArrayList<String> al = new ArrayList<>();
      al.add("srcip");
      al.add("srcport");
      al.add("dstip");
      al.add("dstport");
      al.add("proto");
      return Collections.unmodifiableList(al);
   }

   /**
    * {@inheritDoc}
    * <p>
    * The implementation of this function by this class doesn't do anything
    * since it doesn't have any non-matching features to remove.
    */
   @Override
   public Consumer<FormatAsArff> removeNonMatchingClasses() {
      return (faa)->{}; //Do nothing, don't remove any classes
   }
}