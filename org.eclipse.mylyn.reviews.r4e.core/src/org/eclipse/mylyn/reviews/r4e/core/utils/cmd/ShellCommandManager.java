/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * 	 Ericsson AB - Initial API and Implementation 
 *   Alvaro Sanchez-Leon - Adapted for Review for Eclipse
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.utils.cmd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.Activator;

public class ShellCommandManager {
	private File cmdPath;
	private boolean receivedCancellation = false;
	private Boolean waitforCompletion = false;
	private Process process = null;
	private StreamThread outputStream = null;
	private StreamThread errorStream = null;
	private List<String> command = null;
	private List<String> listResult = null;
	List<String> results = null;
	int executedCount = 0;
	int processExitValue = -1;

	// Two purposes:
	// 1) When waitfor is True i.e. waiting for the process to finished
	// Determines the Waiting time reading the output buffer
	// 2) When the execution is triggered by execute, we wait for a possible
	// cancellation order
	// It determines the maximum waiting time waiting for the command to finish
	private int intervals = 10;
	private int msSleep = 10;

	public ShellCommandManager(String commandPath, List<String> rcommand) {
		super();
		if (commandPath == null) {
			this.cmdPath = null;
		} else {
			this.cmdPath = new File(commandPath);
		}

		this.command = rcommand;
	}

	/**
	 * Use this method when you need to cancel the process using the method cancelExecution(), The loop reading the
	 * Stream buffers will stop, this order don't wait fot the process to finish, therefore providing control to the
	 * user of this interface.
	 * 
	 * @return
	 * @throws IOException
	 */
	public int execute() throws IOException {

		if (outputStream == null) {
			outputStream = new StreamThread();
		}
		if (errorStream == null) {
			errorStream = new StreamThread();
		}

		// Maximum waiting time set e.g. 72000 * 250 = 18000000 ms = 300 minutes
		// this method is normally used in background threads e.g Job
		// the cancellation of a background job can be performed manually as
		// long
		// as the cancelling method is implemented in the Job object and
		// requested via
		// this.cancelExecution method.
		intervals = 72000;
		msSleep = 250;
		return execute(outputStream, errorStream, waitforCompletion);
	}

	public int execute(StreamThread outputStream, StreamThread errorStream, boolean waitfor) throws IOException {

		this.outputStream = outputStream;
		this.errorStream = errorStream;

		waitforCompletion = waitfor;
		// -1: a value different from 0 means not defined yet
		// 0: normal termination
		processExitValue = (waitfor) ? -1 : 0;

		ProcessBuilder pb = new ProcessBuilder(command);

		if (cmdPath != null) {
			// Debug.print("ShellCommandManager.execute() drive "
			// + drive.getAbsolutePath());
			pb.directory(cmdPath);
		}

		try {
			process = pb.start();

			// Get exec output
			if (outputStream != null) {
				outputStream.setStream(process.getInputStream());
				outputStream.start();
			}

			// Get exec error
			if (errorStream != null) {
				errorStream.setStream(process.getErrorStream());
				errorStream.start();
			}

			if (waitfor) {
				processExitValue = process.waitFor();
			}

			// Allow the threads to finish reading before processing the
			// command results
			int iwaitCnt = 0;
			while (errorStream != null && errorStream.isAlive()
					&& (iwaitCnt < intervals) && receivedCancellation == false) {
				iwaitCnt++;
				Thread.sleep(msSleep);
			}

			Activator.fTracer.traceInfo("\nWaited " + iwaitCnt
					* msSleep + " ms to read the errorStream");

			iwaitCnt = 0;
			while (outputStream != null && outputStream.isAlive()
					&& (iwaitCnt < intervals) && receivedCancellation == false) {
				iwaitCnt++;
				Thread.sleep(msSleep);
			}

			Activator.fTracer.traceInfo("\nWaited " + iwaitCnt * msSleep + " ms to read the outputStream");
			Activator.fTracer.traceInfo("Returning with exitValue " + processExitValue + "  \"waitfor\"=" + waitfor
					+ "\npath:" + cmdPath + "\n" + command.toString());
			if (receivedCancellation) {
				Activator.fTracer.traceInfo("\nShellCommandManager.execute : Cancellation Order Received");
			}

			executedCount++;
		} catch (InterruptedException e) {
			// waitFor()
			Activator.fTracer.traceInfo("ShellCommandManager.execute() InterruptedException:"
					+ e.getMessage());
			e.printStackTrace();
		}

		return processExitValue;
	}

	/**
	 * Order cancellation of the process execution associated to this command
	 * manager
	 */
	public void cancelExecution() {
		receivedCancellation = true;
		if (process != null) {
			process.destroy();
		}
		Activator.fTracer.traceInfo("Cancelling execution for : "
				+ command.toString() + " at " + cmdPath);
	}

	public boolean isCancelled() {
		return receivedCancellation;
	}

	public List<String> getResults(boolean notifyIfError) throws IOException {
		// If execute method has not completed at least once,
		// there are no results to provide
		if (executedCount == 0) {
			return null;
		}

		if (outputStream == null) {
			Activator.fTracer.traceInfo("ShellCommandManager.getResults() outputStream is NULL");
			return null;
		}

		if (errorStream == null) {
			Activator.fTracer.traceInfo("ShellCommandManager.getResults() errorStream is NULL");
			return null;
		}

		if (!(outputStream.isAlive() || errorStream.isAlive())) {
			String str = "ShellCommandManager.getResults() outputStream  + errorStream CLOSED properly, Java Process exit value: ";
			Activator.fTracer.traceInfo(str + processExitValue);
		} else {
			// so this should not happen unless the waiting period have elapsed,
			// or the command is cancelled
			if (outputStream.isAlive()) {
				Activator.fTracer
						.traceInfo("ShellCommandManager.getResults() outputStream is alive: Java Process exit value: "
								+ processExitValue);
			}

			if (errorStream.isAlive()) {
				Activator.fTracer
						.traceInfo("ShellCommandManager.getResults() errorStream is alive: Java Process exit value: "
								+ processExitValue);
			}
		}
		// Print error, if any
		if (notifyIfError) {
			List<String> lines = errorStream.getLines();
			if (lines == null) {
				Activator.fTracer.traceInfo("ShellCommandManager.getResults() error stream lines size: NULL");
			} else {
				Activator.fTracer.traceInfo("ShellCommandManager.getResults() error stream lines size: "
								+ lines.size());
			}
			if (lines != null && lines.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (String s : lines) {
					sb = sb.append(s + "\n");
				}
				Activator.fTracer.traceInfo("ShellCommandManager.getResults() " + sb);
				throw new IOException(
						"R4E reporting error when attempting to execute command. Operation could not be completed. "
										+ command.toString() + "\n" + sb);
			}
		}

		return outputStream.getLines();
	}

	public void setSVNResult(List<String> result) {
		listResult = result;
	}

	public List<String> getSVNResult() {
		return listResult;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// String[] cmd1 = new String[] { "cleartool", "lsactivity", "-fmt",
		// "\"%[versions]p\n\"", "activity:act_1149873639" };
		// String[] cmd2 = new String[] { "pwd" };

		// String command3 = new String(
		// "cleartool lsvtree -a -merge -nco -obs
		// S:/bscng_cmf/com_swb/com_swu/src/cmfbaseconfigmgr.cc@@\\main\\r7a_int\\r9a_int\\2");

		String command3 = new String(
				"cleardiff -blank_ignore -diff_format /view/Alvaro Sanchez-Leon_svp1code_lvb/vobs/r4ecode/dev/r4e/src/com/ericsson/r4e/core/header/column/UserNameTableModel.java@@/main/svp1code/svp1code_lvb/0 /view/Alvaro Sanchez-Leon_svp1code_lvb/vobs/r4ecode/dev/r4e/src/com/ericsson/r4e/core/header/column/UserNameTableModel.java@@/main/svp1code/svp1code_lvb/1");

		String command4 = new String(
				"cleardiff -blank_ignore -diff_format /view/Alvaro Sanchez-Leon_svp1code_lvb/vobs/r4ecode/dev/r4e/src/com/ericsson/r4e/core/header/column/UserNameTableModel.java@@/main/svp1code/svp1code_lvb/0 /view/Alvaro Sanchez-Leon_svp1code_lvb/vobs/r4ecode/dev/r4e/src/com/ericsson/r4e/core/header/column/UserNameTableModel.java@@/main/svp1code/svp1code_lvb/1");

		String[] cmd3 = command3.split(" ");
		String[] cmd4 = command4.split(" ");
		List<String> outputLines = new ArrayList<String>();

		StreamThread outputStream = new StreamThread();
		StreamThread errorStream = new StreamThread();

		// Command 3

		// Note: Arrays.asList(s)return a fix size list which
		// cannot be resized by list.add or list.remove
		List<String> command = Arrays.asList(cmd3);
		int status = 0;
		long elapsedTime = 0;
		long startTime = System.currentTimeMillis();
		ShellCommandManager manager = new ShellCommandManager(null, command);
		try {
			status = manager.execute(outputStream, errorStream, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		elapsedTime = System.currentTimeMillis() - startTime;
		Activator.fTracer.traceInfo("exec() status " + status + ", elapsed time "
						+ elapsedTime);

		startTime = System.currentTimeMillis();
		outputLines = outputStream.getLines();
		elapsedTime = System.currentTimeMillis() - startTime;
		Activator.fTracer.traceInfo("exec() result time " + elapsedTime);

		if (outputStream.isAlive() || outputStream.isAlive()) {
			System.err.println("exec() outputStream alive ");
		}

		for (String s : outputLines) {
			Activator.fTracer.traceInfo(">" + s);
		}

		// Command 4
		StreamThread outputStream4 = new StreamThread();
		StreamThread errorStream4 = new StreamThread();

		// Note: Arrays.asList(s)return a fix size list which
		// cannot be resized by list.add or list.remove
		command = Arrays.asList(cmd4);
		status = 0;
		elapsedTime = 0;
		startTime = System.currentTimeMillis();
		manager = new ShellCommandManager(null, command);
		try {
			status = manager.execute(outputStream4, errorStream4, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		elapsedTime = System.currentTimeMillis() - startTime;
		Activator.fTracer.traceInfo("exec() status " + status + ", elapsed time "
						+ elapsedTime);

		startTime = System.currentTimeMillis();
		outputLines = outputStream.getLines();
		elapsedTime = System.currentTimeMillis() - startTime;
		Activator.fTracer.traceInfo("exec() result time " + elapsedTime);

		if (outputStream.isAlive() || outputStream.isAlive()) {
			System.err.println("exec() outputStream alive ");
		}

		for (String s : outputLines) {
			Activator.fTracer.traceInfo(">" + s);
		}

		// Command 2
		StreamThread outputStream2 = new StreamThread();
		StreamThread errorStream2 = new StreamThread();

		// Note: Arrays.asList(s)return a fix size list which
		// cannot be resized by list.add or list.remove
		// command = Arrays.asList(cmd2);
		try {
			Activator.fTracer.traceInfo("exec() status " + manager.execute(outputStream2, errorStream2, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (outputStream2.isAlive() || outputStream2.isAlive()) {
			System.err.println("exec() outputStream alive ");
		}
		outputLines = outputStream2.getLines();
		for (String s : outputLines) {
			Activator.fTracer.traceInfo(">" + s);
		}

	}
}
