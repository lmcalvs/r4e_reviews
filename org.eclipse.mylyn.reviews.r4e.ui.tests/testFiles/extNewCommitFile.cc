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

class extNewCommitFile {

public:
	extNewCommitFile() {}

	char getSomeDouble() {
		return someDouble;
	}

	void setSomeDouble(int someDouble) {
		this.someDouble = someDouble;
	}

private:
	double someDouble;
};

int main() {
	extNewCommitFile test = new extNewCommitFile();
	test.setSomeDouble(1.0);
	double testDouble = test.getSomeDouble();
	delete test;
}
