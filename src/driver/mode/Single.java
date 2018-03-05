package driver.mode;

import driver.categoricalType.*;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessNormal;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

public final class Single extends Mode{
   public Single(NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(nl, categoricalType);
   }

   @Override
   public String getSystemType() {
      return "Single";
   }

   @Override
   public List<PreprocessFile> getPreprocessFiles() throws IOException {
      ArrayList<PreprocessFile> pfL = new ArrayList<>();
      pfL.add(new PreprocessNormal());

      pfL.add(new PreprocessTCPFlood());
      pfL.add(new PreprocessUDPFlood());
      pfL.add(new PreprocessHTTPFlood());

//      pfL.add(new PreprocessSlowBody());
      pfL.add(new PreprocessSlowHeaders());
      pfL.add(new PreprocessSlowRead());

      return pfL;
   }
}