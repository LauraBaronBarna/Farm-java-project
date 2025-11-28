import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@Test
public void elements() {
    Use.theEnum("farm.crop.CropType")
       .ofEnumElements("LETTUCE", "CORN", "STRAWBERRY")
       .that(hasUsualModifiers())
       .info("Associated values:")
       .info("`growthRate`       is  3 for LETTUCE,  5 for CORN,  6 for STRAWBERRY")
       .info("`possibleMaturity` is 30 for LETTUCE, 75 for CORN, 60 for STRAWBERRY");
}

void main() {}


