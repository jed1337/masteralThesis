package preprocessFiles;

import constants.GeneralAttackTypeEnum;
import constants.FileNameConstants;
import constants.SpecificAttackTypeEnum;
import java.io.IOException;

public final class PreprocessNormal implements PreprocessFileBuilderStrategy{
   public PreprocessNormal() throws IOException {
      super(FileNameConstants.KDD_TRAIN,
         GeneralAttackTypeEnum.NORMAL,
         "normal"
      );
   }

   @Override
   public PreprocessFile.PreprocessFileBuilder getBuilder() {
      return new PreprocessFile.PreprocessFileBuilder(
         f->f.getKDDTrainPath(),
         GeneralAttackTypeEnum.NORMAL,
         SpecificAttackTypeEnum.NORMAL
      );
   }
}