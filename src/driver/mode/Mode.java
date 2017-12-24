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
import preprocessFiles.PreprocessFile;
import driver.categoricalType.AttackType;

public abstract class Mode {
   protected final int totalCount;
   protected final List<PreprocessFile> pfL;
   
   private final NoiseLevel nl;
   private final AttackType categoricalType;

   public Mode(int totalCount, NoiseLevel nl, AttackType categoricalType) throws IOException {
      this.totalCount = totalCount;
      this.pfL = new ArrayList<>();
      this.nl = nl;
      this.categoricalType = categoricalType;
      
      this.pfL.addAll(this.nl.getPreprocessedFiles());
   }

   protected final void setPreprocessFileCount(){
      Set<Enum<GeneralAttackType>> gats = new HashSet(); // Unique values
      this.pfL.forEach((pf)->{
         gats.add(pf.getGeneralAttackType());
      });

      for (Enum<GeneralAttackType> gat : gats) {
         Predicate<PreprocessFile> sameGAT = (pf)->pf.getGeneralAttackType() == gat;

         int attackTypeCount = (int) this.pfL.stream()
            .filter(sameGAT)
            .count();
         
         this.pfL.stream()
            .filter(sameGAT)
            .forEach((pf)->{
               pf.setInstancesCount(this.totalCount/(gats.size()*attackTypeCount));
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