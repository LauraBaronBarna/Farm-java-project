import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.crop.Crop")
       .that(hasUsualModifiers());
}

@Test
public void fieldType() {
    it.hasField("type: CropType")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldGrowthLevel() {
    it.hasField("growthLevel: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("Initially 0 because no growth occurs without a day passing by.");
}

@Test
public void fieldWaterLevel() {
    it.hasField("waterLevel: int")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("Initially 2.");
}

@Test
public void fieldIsCropDead() {
    it.hasField("isCropDead: boolean")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void fieldDryDays() {
    it.hasField("dryDays: int")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER)
      .info("Initially 0.");
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeFields("type"))
      .that(hasUsualModifiers())
      .thatThrows("IllegalArgumentException", "`type` cannot be `null`.");
}

@Test
public void methodWater() {
    it.hasMethod("water", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("The amount of water can be up to 10.")
      .info("Increases the water amount by 2 and resets `dryDays`.");
}

@Test
public void methodSimulateDay() {
    it.hasMethod("simulateDay", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Growth only occurs if water is available, up to 100 at most. Otherwise, the number of dry days increases by 1. Water decreases by 1 per day.")
      .info("STRAWBERRY, LETTUCE, and CORN have specific maximum allowed dry days: 1, 2, and 3 respectively.")
      .info("If the number of dry days exceeds the allowed limit, the crop dies.")
      .testWith(testCase("testWaterDecayAfterSimulateDay"), "Verify that `simulateDay` decreases the water level by 1.")
      .testWith(testCase("testGrowthOccursWhenWatered"), "Verify that growth increases according to the CropType when a day passes and the crop is watered.")
      .testWith(testCase("testDifferentDeathThresholds"), "Verify that different crops die when their maximum allowed dry days are exceeded.");
}

@Test
public void methodIsMature() {
    it.hasMethod("isMature", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean", "A crop is mature if it is alive and its growth level has reached or exceeded the possible maturity threshold.")
      .testWith(testCase("testIsMature"), "Verify that a crop can reach maturity.");
}

@Test
public void methodHarvest() {
    it.hasMethod("harvest", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean")
      .info("Resets `growthLevel`, `waterLevel`, `dryDays` to their initial values.")
      .testWith(testCase("testHarvestSuccess"), "Verify that the harvest function succeeds when the crop is mature.")
      .testWith(testCase("testHarvestFailsWhenNotMatureOrDead"), "Verify that the harvest function fails when the crop is not mature or is dead.");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION)
      .info("LETTUCE, CORN, and STRAWBERRY return 'L', 'C', or 'S'.")
      .info("Dead crops return 'D'.");
}

void main() {}


