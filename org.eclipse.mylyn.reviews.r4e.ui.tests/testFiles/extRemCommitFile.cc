/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

//This is a test file used by the R4E UI Test framework

class extRemCommitFile {

public:
	extRemCommitFile() {}

	char getSomeChar() {
		return someChar;
	}

	void setSomeChar(int someChar) {
		this.someChar = someChar;
	}

private:
	char someChar;
};

int main() {
	extRemCommitFile test = new extRemCommitFile();
	test.setSomeChar('a');
	char testChar = test.getSomeChar();
	delete test;
}
