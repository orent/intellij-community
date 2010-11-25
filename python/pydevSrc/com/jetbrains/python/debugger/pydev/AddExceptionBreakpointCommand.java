package com.jetbrains.python.debugger.pydev;

import org.jetbrains.annotations.NotNull;

/**
 * @author traff
 */
public class AddExceptionBreakpointCommand extends ExceptionBreakpointCommand {
  final ExceptionBreakpointNotifyPolicy myNotifyPolicy;

  public AddExceptionBreakpointCommand(@NotNull final RemoteDebugger debugger,
                                       @NotNull String exception, @NotNull ExceptionBreakpointNotifyPolicy notifyPolicy) {
    super(debugger, ADD_EXCEPTION_BREAKPOINT, exception);
    myNotifyPolicy = notifyPolicy;
  }

  @Override
  protected void buildPayload(Payload payload) {
    super.buildPayload(payload);
    payload.add(myNotifyPolicy.isNotifyAlways()).add(myNotifyPolicy.isNotifyOnTerminate());
  }

  public static class ExceptionBreakpointNotifyPolicy {
    private final boolean myNotifyAlways;
    private final boolean myNotifyOnTerminate;

    public ExceptionBreakpointNotifyPolicy(boolean notifyAlways, boolean notifyOnTerminate) {
      myNotifyAlways = notifyAlways;
      myNotifyOnTerminate = notifyOnTerminate;
    }

    public boolean isNotifyAlways() {
      return myNotifyAlways;
    }

    public boolean isNotifyOnTerminate() {
      return myNotifyOnTerminate;
    }
  }
}
