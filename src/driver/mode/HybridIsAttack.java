package driver.mode;

import driver.categoricalType.HybridStageIsAttack;
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

public final class HybridIsAttack extends Mode{
   public HybridIsAttack(NoiseLevel nl)
         throws IOException {
      super(nl, new HybridStageIsAttack());
   }

   @Override
   public String getSystemType() {
      return "Hybrid isAttack";
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