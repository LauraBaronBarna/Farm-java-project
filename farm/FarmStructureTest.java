import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.field.Farm")
       .that(hasUsualModifiers())
       .info("A farm containing a 2D field of `Plot`s. Each plot may contain a `Crop` or be empty.");
}

@Test
public void fieldField() {
    it.hasField("field: array of array of Plot")
      .thatIs(INSTANCE_LEVEL, FULLY_IMPLEMENTED, NOT_MODIFIABLE, VISIBLE_TO_NONE)
      .thatHas(GETTER)
      .thatHasNo(SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withParams("rows: int", "cols: int"))
      .that(hasUsualModifiers())
      .thatThrows("IllegalArgumentException", "If either `rows` or `columns` are below 1.")
      .info("Initializes the 2D field with all new `Plot` instances.");
}

@Test
public void methodSimulateDay() {
    it.hasMethod("simulateDay", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Simulates a day on the farm, updating all crops accordingly.");
}

@Test
public void methodFindMostThirstyCrop() {
    it.hasMethod("findMostThirstyCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("farm.crop.Crop", "`null` if no crops are present or all are dead. Otherwise the crop with the lowest water level that is alive.");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION)
      .info("""
          Returns a textual representation of the farm's field as a grid. E.g:
          E E E        S D E
          E E E   or   L E C   .....
          E E E        C S L
      """);
}

void main() {}


