import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.person.Farmer")
       .that(hasUsualModifiers())
       .info("""
           Represents a farmer who can plant, water, harvest, and manage crops on a farm.
           Keeps track of harvested crops and interacts with the farm's plots.
       """);
}

@Test
public void fieldName() {
    it.hasField("name: String")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldFarm() {
    it.hasField("farm: farm.field.Farm")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHasNo(GETTER, SETTER)
      .info("The farm managed by the farmer.");
}

@Test
public void fieldHarvestedCrops() {
    it.hasField("harvestedCrops: ArrayList of farm.crop.Crop")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("A list of crops that have been harvested by the farmer.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields("name", "farm"))
      .that(hasUsualModifiers())
      .thatThrows("IllegalArgumentException", "If name is null/blank or farm is `null`.")
      .info("Sets `harvestedCrops` as empty, the other fields from the args.");
}

@Test
public void methodPlantCrop() {
    it.hasMethod("plantCrop", withParams("row: int", "col: int", "type: farm.crop.CropType"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .thatThrows("IllegalStateException", "If the plot is already occupied.")
      .info("Plants a new crop of the specified type at the given row and column.");
}

@Test
public void methodWaterCrop() {
    it.hasMethod("waterCrop", withParams("row: int", "col: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Waters the crop at the specified location if it exists and is not dead.")
      .testWith(testCase("testPlantCropAndWater"), """
          Plant a crop. Verify it's there.
          Then water it. Verify that its water level increases.
      """);
}

@Test
public void methodHarvestCrop() {
    it.hasMethod("harvestCrop", withParams("row: int", "col: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Harvests the crop at the specified location if it is mature, adding it to `harvestedCrops`.")
      .testWith(testCase("testHarvestRemovesMatureCrop"), "Harvesting a mature crop empties the plot.");
}

@Test
public void methodWaterCrops() {
    it.hasMethod("waterCrops", withParams("type: farm.crop.CropType"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Waters all crops of `type` in the entire farm that are not dead.");
}

@Test
public void methodHarvestCrops() {
    it.hasMethod("harvestCrops", withParams("type: farm.crop.CropType"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Harvests all mature crops of `type` in the farm and adds them to `harvestedCrops`.");
}

@Test
public void methodCleanPlot() {
    it.hasMethod("cleanPlot", withParams("row: int", "col: int"))
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("If the specified plot has a dead crop, removes it.")
      .testWith(testCase("testCleanDeadCrop"), "Verify that a dead crop can be removed from the plot.");
}

@Test
public void methodSimulateDay() {
    it.hasMethod("simulateDay", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Simulates a day for all crops on the farm.")
      .testWith(testCase("testStory"), """
          Plant two crops. Water them on every `interval` day for `days` amount of days. Then expect to harvest `expected` crops.
          Use the following test cases. (The comments are for notes only.)
          LETTUCE,       CORN, 2, 14, 1 // Both survive, only one is mature
          LETTUCE,       CORN, 2, 15, 2 // Both survive, both are mature
          STRAWBERRY,    CORN, 4, 27, 0 // Strawberry dies, corn survives but is not mature
          STRAWBERRY,    CORN, 4, 28, 1 // Strawberry dies, corn survives and is mature
          STRAWBERRY, LETTUCE, 5,  8, 0 // Both dry out, none are harvested
      """);
}

@Test
public void methodWaterMostThirstyCrop() {
    it.hasMethod("waterMostThirstyCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Finds the crop with the lowest water level in the farm and waters it if present.")
      .testWith(testCase("testWaterMostThirstyCrop"), "Verify that the most thirsty crop is watered.");
}

void main() {}


