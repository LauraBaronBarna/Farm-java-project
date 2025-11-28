import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    // usedLang = Lang.EN; // uncomment to enforce the message language
    Use.theClass("farm.field.Plot")
       .that(hasUsualModifiers())
       .info("Represents a single plot in the farm.");
}

@Test
public void fieldCrop() {
    it.hasField("crop: farm.crop.Crop")
      .that(hasUsualModifiers())
      .thatHas(GETTER)
      .thatHasNo(SETTER)
      .info("Can be `null` which indicates an empty plot.");
}

@Test
public void constructor() {
    it.hasConstructor(withNoParams())
      .that(hasUsualModifiers());
}

@Test
public void methodIsEmpty() {
    it.hasMethod("isEmpty", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean", "`true` exactly if the plot has no crop.");
}

@Test
public void methodPlant() {
    it.hasMethod("plant", withArgsLikeAllFields())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Plants a crop in the plot.")
      .info("throws `IllegalArgumentException` if given `null`.")
      .info("throws `IllegalStateException` if the plot is already occupied.");
}
// -------- seguir desde aqui 
@Test
public void methodRemoveCrop() {
    it.hasMethod("removeCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturnsNothing()
      .info("Removes the crop from the plot by setting it to `null`, leaving it empty.")
      .info("throws `IllegalStateException` if the plot is not occupied.");
}

@Test
public void methodHasDeadCrop() {
    it.hasMethod("hasDeadCrop", withNoParams())
      .that(hasUsualModifiers())
      .thatReturns("boolean", "`true` exactly if the plot contains a dead crop.");
}

@Test
public void text() {
    it.has(TEXTUAL_REPRESENTATION)
      .info("A single `E` if empty, otherwise the crop's text.");
}

void main() {}


