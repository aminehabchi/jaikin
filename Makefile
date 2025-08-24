JAVAC = javac
JAVA = java

BUILD_DIR = build

SRC = $(wildcard *.java helpers/*.java)

MAIN = Jaikin

CLASSES = $(SRC:%.java=$(BUILD_DIR)/%.class)

all: $(CLASSES)

$(BUILD_DIR)/%.class: %.java
	@mkdir -p $(dir $@)
	$(JAVAC) -d $(BUILD_DIR) $<

run: all
	$(JAVA) -cp $(BUILD_DIR) $(MAIN)

clean:
	rm -rf $(BUILD_DIR)

rerun: clean all
	$(JAVA) -cp $(BUILD_DIR) $(MAIN)
