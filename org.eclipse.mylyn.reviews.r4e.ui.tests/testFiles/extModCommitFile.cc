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

class extModCommitFile {

public:
	extModCommitFile() {}

	int getSomeInt() {
		return someInt;
	}

	void setSomeInt(int someInt) {
		this.someInt = someInt;
	}

private:
	int someInt;
};

int main() {
	extModCommitFile test = new extModCommitFile();
	test.setSomeInt(1);
	int testInt = test.getSomeInt();
	delete test;
}
