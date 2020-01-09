JFLAGS =-g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
	
CLASSES = Node.java Node2.java RedBlackTree.java minHeap.java risingCity.java
default: classes
classes: $(CLASSES:.java=.class)

	
	