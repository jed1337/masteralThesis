package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.AttackType;
import preprocessFiles.PreprocessFile;

public abstract class Mode {
   protected final int totalCount;
   protected ArrayList<PreprocessFile> pfAL;

   public Mode(int totalCount, NoiseLevel nl) throws IOException {
      this.totalCount = totalCount;
      this.pfAL = new ArrayList<>();
      this.pfAL.addAll(nl.getPreprocessedFiles());
   }

   protected final void setPreprocessFileCount(){
      Set<Enum<AttackType>> attackTypes = new HashSet(); // Unique values
      this.pfAL.forEach((pf)->{
         attackTypes.add(pf.getSpecificAttackType());
      });

      for (Enum<AttackType> attackType : attackTypes) {
         Predicate<PreprocessFile> sameAttackType = (pf)->pf.getSpecificAttackType() == attackType;

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

   public abstract ArrayList<PreprocessFile> getPreprocessFiles() throws IOException;
   public abstract String getReplacement();
}