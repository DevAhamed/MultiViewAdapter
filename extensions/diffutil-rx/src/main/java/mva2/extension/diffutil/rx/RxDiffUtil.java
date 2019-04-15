/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mva2.extension.diffutil.rx;

import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import mva2.adapter.internal.DiffUtilCallback;
import mva2.adapter.util.MvaDiffUtil;
import mva2.adapter.util.PayloadProvider;

public class RxDiffUtil<M> implements MvaDiffUtil<M> {

  private final PayloadProvider<M> payloadProvider;
  private final PublishProcessor<DiffRequest<M>> pp = PublishProcessor.create();
  private final Flowable<DiffRequest<M>> out = pp.onBackpressureBuffer();
  private Disposable disposable;

  public RxDiffUtil(PayloadProvider<M> payloadProvider) {
    this.payloadProvider = payloadProvider;
    subscribeToFlowable();
  }

  public Disposable getDisposable() {
    return disposable;
  }

  @Override
  public void calculateDiff(final ListUpdateCallback listUpdateCallback, final List<M> oldList,
      final List<M> newList) {
    DiffRequest<M> diffRequest = new DiffRequest<>(listUpdateCallback, oldList, newList);
    if (pp.hasSubscribers()) {
      pp.onNext(diffRequest);
    }
  }

  private void subscribeToFlowable() {
    disposable = out.map(new Function<DiffRequest<M>, DiffResult>() {
      @Override public DiffResult apply(DiffRequest<M> diffRequest) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(
            new DiffUtilCallback<M>(diffRequest.oldList, diffRequest.newList) {
              @Override public boolean areItemsTheSame(M oldItem, M newItem) {
                return payloadProvider.areItemsTheSame(oldItem, newItem);
              }

              @Override public boolean areContentsTheSame(M oldItem, M newItem) {
                return payloadProvider.areContentsTheSame(oldItem, newItem);
              }

              @Override public Object getChangePayload(M oldItem, M newItem) {
                return payloadProvider.getChangePayload(oldItem, newItem);
              }
            });
        return new DiffResult(diffRequest.listUpdateCallback, result);
      }
    })
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<DiffResult>() {
          @Override public void accept(DiffResult diffResult) {
            diffResult.diffResult.dispatchUpdatesTo(diffResult.listUpdateCallback);
          }
        });
  }

  private static final class DiffRequest<M> {

    private final ListUpdateCallback listUpdateCallback;
    private final List<M> oldList;
    private final List<M> newList;

    private DiffRequest(ListUpdateCallback listUpdateCallback, List<M> oldList, List<M> newList) {
      this.listUpdateCallback = listUpdateCallback;
      this.oldList = oldList;
      this.newList = newList;
    }
  }

  private static final class DiffResult {

    private final ListUpdateCallback listUpdateCallback;
    private final DiffUtil.DiffResult diffResult;

    private DiffResult(ListUpdateCallback listUpdateCallback, DiffUtil.DiffResult diffResult) {
      this.listUpdateCallback = listUpdateCallback;
      this.diffResult = diffResult;
    }
  }
}
