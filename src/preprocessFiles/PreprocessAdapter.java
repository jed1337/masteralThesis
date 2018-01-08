package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.SpecificAttackTypeEnum;

public class PreprocessAdapter implements PreprocessFileBuilderStrategy{
   private final String path;
   private final GeneralAttackTypeEnum genAttack;
   private final SpecificAttackTypeEnum specAttack;

   public PreprocessAdapter(String path, GeneralAttackTypeEnum genAttack, SpecificAttackTypeEnum specAttack){
      this.path = path;
      this.genAttack = genAttack;
      this.specAttack = specAttack;
   }

   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->path,
         genAttack,
         specAttack
      );
   }
}