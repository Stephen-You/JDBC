package com.jdbc.practice.ThreadLocal;

import org.junit.Test;

public class ThreadLocalPractice {
	//���ܶ��߳���Ҫ���ʹ��ͬһ�����󣬲�����Ҫ�ö��������ͬ��ʼ��ֵ��ʱ�����ʺ�ʹ��ThreadLocal
	@Test
	public void test() throws InterruptedException{
		/*
		 * �ڲ�ʹ�õ�ǰ�߳�Ϊkey�������Ǳ����ֵΪvalue
		 */
		final ThreadLocal<String> local = new ThreadLocal<>();
		local.set("local1");
		new Thread(){
			public void run(){
				local.set("local2");
				System.out.println(local.get());
			}
		}.start();
		Thread.sleep(1000);
		System.out.println(local.get());
	}
}