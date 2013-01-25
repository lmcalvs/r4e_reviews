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

class extNoVCSFile {

public:
	extNoVCSFile() {}

	int getSomeFloat() {
		return someFloat;
	}

	void setSomeFloat(int someFloat) {
		this.someFloat = someFloat;
	}

private:
	float someFloat;
};

int main() {
	extNoVCSFile test = new extNoVCSFile();
	test.setSomefloat(1.0);
	int testFloat = test.getSomeFloat();
	delete test;
}
