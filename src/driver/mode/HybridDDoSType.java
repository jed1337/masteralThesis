package driver.mode;

import driver.mode.noiseLevel.NoNoise;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import driver.categoricalType.CategoricalType;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class HybridDDoSType extends Mode{
   public HybridDDoSType(CategoricalType categoricalType) throws IOException {
      super(NoNoise.getInstance(), categoricalType);
   }

   @Override
   public String getSystemType() {
      return "Hybrid DDoS Type";
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      ArrayList<PreprocessFile> pfL = new ArrayList<>();
      pfL.add(new PreprocessTCPFlood());
      pfL.add(new PreprocessUDPFlood());
      pfL.add(new PreprocessHTTPFlood());
      
      pfL.add(new PreprocessSlowBody());
      pfL.add(new PreprocessSlowHeaders());
      pfL.add(new PreprocessSlowRead());
      return pfL;
   }
}