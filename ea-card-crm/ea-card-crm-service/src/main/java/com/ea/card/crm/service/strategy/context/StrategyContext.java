package com.ea.card.crm.service.strategy.context;

/**
 * 策略模式上下文
 * @author huacheng.li
 *
 */
public class StrategyContext<T> {
	
	/**
	 * 持有一个具体策略的对象
	 */
	private T strategyContext;
	
	/**
     * 构造函数，传入一个具体策略对象
     * @param strategy   具体策略对象
     */
	public StrategyContext(T strategy) {
		
		this.strategyContext = strategy;
	}
	
	/**
	 * 获取策略上下文对象
	 * @return
	 */
	public T getStrategyContext() {
		return strategyContext;
	}
	
	
}
