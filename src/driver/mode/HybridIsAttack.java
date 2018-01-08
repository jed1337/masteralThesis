package driver.mode;

import driver.categoricalType.HybridStageIsAttack;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessNormal;
import preprocessFiles.PreprocessSlowBody;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

<<<<<<< HEAD
public final class HybridIsAttack extends SystemType{
   public HybridIsAttack(NoiseLevel nl) throws IOException {
      super(nl, new HybridStageIsAttack());
      
      super.pfBS.add(new PreprocessNormal());
=======
public final class HybridIsAttack extends Mode{
   public HybridIsAttack(int totalInstancesCount, NoiseLevel nl)
         throws IOException {
      super(totalInstancesCount, nl, new HybridStageIsAttack());

      super.pfL.add(new PreprocessNormal());
>>>>>>> parent of f698a90... Before trying NetMate featuers
      
      super.pfBS.add(new PreprocessTCPFlood());
      super.pfBS.add(new PreprocessUDPFlood());
      super.pfBS.add(new PreprocessHTTPFlood());
      
<<<<<<< HEAD
      super.pfBS.add(new PreprocessSlowBody());
      super.pfBS.add(new PreprocessSlowHeaders());
      super.pfBS.add(new PreprocessSlowRead());
=======
      super.pfL.add(new PreprocessSlowBody());
      super.pfL.add(new PreprocessSlowHeaders());
      super.pfL.add(new PreprocessSlowRead());
      
      new HybridStageIsAttack().setPreprocessFileCount(super.pfL, totalInstancesCount);
      System.out.println("");
>>>>>>> parent of f698a90... Before trying NetMate featuers
   }
   
   @Override
   public String getSystemType() {
      return "Hybrid isAttack";
   }
}