package org.jeometry.common.data.refresh;

import reactor.core.publisher.Mono;

public interface Refreshable {

  default String getLabel() {
    return toString();
  }

  void refresh();

  void refreshIfNeeded();

  default Mono<Boolean> refreshIfNeeded$() {
    return Mono.fromCallable(() -> {
      refreshIfNeeded();
      return true;
    });
  }
}
