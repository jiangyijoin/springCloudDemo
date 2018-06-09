package com.chinaoly.frm.core.utils.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinaoly.frm.common.utils.ListUtils;
/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class MultiReader {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private int batchSize;

	private int hllBatchSize;

	private PipelineReader pipelineReader;

	private ExecutorService executorService;

	public int getHllBatchSize() {
		return hllBatchSize;
	}

	public PipelineReader getPipelineReader() {
		return pipelineReader;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public MultiReader(PipelineReader pipelineReader, int batchSize, int hllBatchSize, int multiThreads) {
		this.pipelineReader = pipelineReader;
		this.batchSize = batchSize;
		this.hllBatchSize = hllBatchSize;
		if (multiThreads > 0) {
			this.executorService = Executors.newFixedThreadPool(multiThreads);
		} else {
			this.executorService = new ThreadPoolExecutor(0, 1000, 60L, TimeUnit.SECONDS,
					new SynchronousQueue<Runnable>());
		}
	}

	public Map<String, Map<String, String>> hgetAll(List<String> keys) {
		try {
			List<List<String>> keysList = ListUtils.fixedSize(keys, batchSize);
			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			List<Callable<Map<String, Map<String, String>>>> tasks = new ArrayList<Callable<Map<String, Map<String, String>>>>(
					keysList.size());
			for (List<String> keyList : keysList) {
				tasks.add(new HgetAllTask(keyList));
			}
			List<Future<Map<String, Map<String, String>>>> futures = executorService.invokeAll(tasks);
			for (Future<Map<String, Map<String, String>>> future : futures) {
				result.putAll(future.get());
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Map<String, String>> hmget(List<String> keys, String... fields) {
		try {
			List<List<String>> keysList = ListUtils.fixedSize(keys, batchSize);
			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			List<Callable<Map<String, Map<String, String>>>> tasks = new ArrayList<Callable<Map<String, Map<String, String>>>>(
					keysList.size());
			for (List<String> keyList : keysList) {
				tasks.add(new HmgetTask(keyList, fields));
			}
			List<Future<Map<String, Map<String, String>>>> futures = executorService.invokeAll(tasks);
			for (Future<Map<String, Map<String, String>>> future : futures) {
				result.putAll(future.get());
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Map<byte[], byte[]>> hgetAllByte(List<String> keys) {
		try {
			List<List<String>> keysList = ListUtils.fixedSize(keys, batchSize);
			Map<String, Map<byte[], byte[]>> result = new HashMap<String, Map<byte[], byte[]>>();
			List<Callable<Map<String, Map<byte[], byte[]>>>> tasks = new ArrayList<Callable<Map<String, Map<byte[], byte[]>>>>(
					keysList.size());
			for (List<String> keyList : keysList) {
				tasks.add(new HgetAllByteTask(keyList));
			}
			List<Future<Map<String, Map<byte[], byte[]>>>> futures = executorService.invokeAll(tasks);
			for (Future<Map<String, Map<byte[], byte[]>>> future : futures) {
				result.putAll(future.get());
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	class HgetAllTask implements Callable<Map<String, Map<String, String>>> {

		private List<String> keys;

		public HgetAllTask(List<String> keys) {
			this.keys = keys;
		}

		@Override
		public Map<String, Map<String, String>> call() throws Exception {
			return pipelineReader.batchRead(keys);
		}

	}

	class HmgetTask implements Callable<Map<String, Map<String, String>>> {

		private List<String> keys;

		private String[] fields;

		public HmgetTask(List<String> keys, String[] fields) {
			this.keys = keys;
			this.fields = fields;
		}

		@Override
		public Map<String, Map<String, String>> call() throws Exception {
			return pipelineReader.batchRead(keys, fields);
		}

	}

	class HgetAllByteTask implements Callable<Map<String, Map<byte[], byte[]>>> {

		private List<String> keys;

		public HgetAllByteTask(List<String> keys) {
			this.keys = keys;
		}

		@Override
		public Map<String, Map<byte[], byte[]>> call() throws Exception {
			return pipelineReader.batchReadByte(keys);
		}

	}

}
