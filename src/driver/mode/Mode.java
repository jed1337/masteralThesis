package driver.mode;

import driver.mode.noiseLevel.NoiseLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import constants.GeneralAttackType;
import driver.categoricalType.CategoricalType;
import preprocessFiles.PreprocessFile;

public abstract class Mode {
   protected final int totalCount;
   protected final List<PreprocessFile> pfL;
   
   private final NoiseLevel nl;
   private final CategoricalType categoricalType;

   public Mode(int totalCount, NoiseLevel nl, CategoricalType categoricalType) throws IOException {
      this.totalCount = totalCount;
      this.pfL = new ArrayList<>();
      this.nl = nl;
      this.categoricalType = categoricalType;
      
      this.pfL.addAll(this.nl.getPreprocessedFiles());
   }

   protected final void setPreprocessFileCount(){
      Set<Enum<GeneralAttackType>> attackTypes = new HashSet(); // Unique values
      this.pfL.forEach((pf)->{
         attackTypes.add(pf.getGeneralAttackType());
      });

      for (Enum<GeneralAttackType> attackType : attackTypes) {
         Predicate<PreprocessFile> sameAttackType = (pf)->pf.getGeneralAttackType() == attackType;

         int attackTypeCount = (int) this.pfL.stream()
            .filter(sameAttackType)
            .count();
         
         this.pfL.stream()
            .filter(sameAttackType)
            .forEach((pf)->{
               pf.setInstancesCount(this.totalCount/(attackTypes.size()*attackTypeCount));
            }
         );
      }
   }
   
   public final List<PreprocessFile> getPreprocessFiles() throws IOException{
      return Collections.unmodifiableList(this.pfL);
   }
   
   public final float getNoiseLevelFloat(){
      return this.nl.getNoiseLevelFloat();
   }
   
   public final String getNoiseLevelString(){
      return this.nl.getNoiseLevelString();
   }
   
   public final String getRelabel(){
      return this.categoricalType.getRelabel(this.pfL);
   }

//   public abstract String getRelabel();
   public abstract String getSystemType();
}