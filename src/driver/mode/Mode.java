package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.GeneralAttackType;
import preprocessFiles.PreprocessFile;

public abstract class Mode {
   protected final int totalCount;
   protected List<PreprocessFile> pfAL;

   public Mode(int totalCount, NoiseLevel nl) throws IOException {
      this.totalCount = totalCount;
      this.pfAL = new ArrayList<>();
      this.pfAL.addAll(nl.getPreprocessedFiles());
   }

   protected final void setPreprocessFileCount(){
      Set<Enum<GeneralAttackType>> attackTypes = new HashSet(); // Unique values
      this.pfAL.forEach((pf)->{
         attackTypes.add(pf.getGeneralAttackType());
      });

      for (Enum<GeneralAttackType> attackType : attackTypes) {
         Predicate<PreprocessFile> sameAttackType = (pf)->pf.getGeneralAttackType() == attackType;

         int attackTypeCount = (int) this.pfAL.stream()
            .filter(sameAttackType)
            .count();
         this.pfAL.stream()
            .filter(sameAttackType)
            .forEach((pf)->{
               pf.setInstancesCount(this.totalCount/(attackTypes.size()*attackTypeCount));
            });
      }
   }

   public abstract String getReplacement();
   
   public final List<PreprocessFile> getPreprocessFiles() throws IOException{
      return Collections.unmodifiableList(this.pfAL);
   }
   
   public abstract String getSystemType();
}