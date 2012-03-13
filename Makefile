CC=javac
CFLAGS=-g:none
SRC=src
LIB=lib
RES=resources
BINDIR=bin
LSTF=temp.txt
IMGDIR=$(RES)/images
MANIFEST=$(RES)/Manifest.txt
VERSIONFILE=$(RES)/version.txt
VERSION=`cat $(VERSIONFILE)`
NAME=RSBot
DIST=$(LIB)/$(NAME).jar

.PHONY: all Bot Bundle clean

all: Bundle

Bot:
	@if [ ! -d "$(BINDIR)" ]; then mkdir "$(BINDIR)"; fi
	$(CC) $(CFLAGS) -d "$(BINDIR)" `find "$(SRC)" -name \*.java`

Bundle: Bot
	@rm -fv "$(LSTF)"
	@cp "$(MANIFEST)" "$(LSTF)"
	@echo "Specification-Version: \"$(VERSION)\"" >> "$(LSTF)"
	@echo "Implementation-Version: \"$(VERSION)\"" >> "$(LSTF)"
	@if [ -e "$(DIST)" ]; then rm -fv "$(DIST)"; fi
	jar cfm "$(DIST)" "$(LSTF)" -C "$(BINDIR)" . "$(VERSIONFILE)" "$(IMGDIR)"/*
	@rm -f "$(LSTF)"

clean:
	@rm -fv "$(DIST)"
	@rm -rfv "$(BINDIR)"
