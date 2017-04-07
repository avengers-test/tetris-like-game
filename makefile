main = Tetris

# Directories
SRCDIR = src
OBJDIR = bin

JFLAGS = -g -d $(OBJDIR)/
JC = javac

default: obj | classes

# create output directory if it doesn't exist
obj:
	@mkdir -p $(OBJDIR)

classes:
		$(JC) $(JFLAGS) $(SRCDIR)/*.java

run: classes | start

start: 
	java -cp $(OBJDIR)/ $(main)

clean:
		$(RM) $(OBJDIR)/*.class
