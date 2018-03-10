package driver.mode;

import driver.categoricalType.*;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessNormal;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

public final class Single extends Mode{
<<<<<<< HEAD
   public Single(NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(nl, categoricalType);
=======
   public Single(int totalInstancesCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      super(totalInstancesCount, nl, categoricalType);
      
      super.pfL.add(new PreprocessNormal());
      
      super.pfL.add(new PreprocessTCPFlood());
      super.pfL.add(new PreprocessUDPFlood());
      super.pfL.add(new PreprocessHTTPFlood());
      
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());
      
      categoricalType.setPreprocessFileCount(super.pfL, totalInstancesCount);
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
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