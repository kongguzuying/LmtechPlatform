package com.lmtech.infrastructure.service.impl;

import com.lmtech.infrastructure.dao.CodeItemDao;
import com.lmtech.infrastructure.dao.CodeTypeDao;
import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;
import com.lmtech.infrastructure.service.CodeService;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CodeTypeDao codeTypeDao;
	@Autowired
	private CodeItemDao codeItemDao;

	@Override
	public List<CodeType> getCodes() {
		List<CodeType> codeTypes = codeTypeDao.selectAll();
		List<CodeItem> codeItems = codeItemDao.selectAll();

		if (!CollectionUtil.isNullOrEmpty(codeTypes) && !CollectionUtil.isNullOrEmpty(codeItems)) {
			for (CodeType codeType : codeTypes) {
				List<CodeItem> items = new ArrayList<CodeItem>();
				for (CodeItem codeItem : codeItems) {
					if (codeItem.getTypeCode().equals(codeType.getCode())) {
						items.add(codeItem);
					}
				}
				codeType.setCodeItems(items);
			}
		}

		return codeTypes;
	}

	@Override
	public List<CodeItem> getCodeItemOfType(String typeCode) {
		List<CodeItem> codeItems = new ArrayList<CodeItem>();
		getCodeItemsAndParent(typeCode, codeItems);
		
		Map<String, CodeItem> mapCodeItems = new HashMap<String, CodeItem>();
		//子类代码荐覆盖父类代码项
		for (CodeItem codeItem : codeItems) {
			mapCodeItems.put(codeItem.getCode(), codeItem);
		}
		//排序
		List<CodeItem> result = (List<CodeItem>) CollectionUtil.getMapListValue(mapCodeItems);
		Collections.sort(result, new Comparator<CodeItem>() {
			@Override
			public int compare(CodeItem o1, CodeItem o2) {
				return o1.getSortNo() - o2.getSortNo();
			}
		});
		return result;
	}

	@Override
	public String getCodeItemText(String typeCode, String codeItemValue) {
		List<CodeItem> codeItems = getCodeItemOfType(typeCode);

		if (!CollectionUtil.isNullOrEmpty(codeItems)) {
			for (CodeItem codeItem : codeItems) {
				if (StringUtil.equalsIgnoreNullCase(codeItem.getValue(), codeItemValue)) {
					return codeItem.getName();
				}
			}
		}
		return null;
	}

	@Override
	public String getCodeItemValue(String typeCode, String codeItemName) {
		List<CodeItem> codeItems = getCodeItemOfType(typeCode);

		if (!CollectionUtil.isNullOrEmpty(codeItems)) {
			for (CodeItem codeItem : codeItems) {
				if (StringUtil.equalsIgnoreNullCase(codeItem.getName(), codeItemName)) {
					return codeItem.getValue();
				}
			}
		}
		return null;
	}

	private void getCodeItemsAndParent(String typeCode, List<CodeItem> parentCodeItems) {
		CodeType codeType = codeTypeDao.selectById(typeCode);
		if (codeType != null && !StringUtil.isNullOrEmpty(codeType.getParentCode())) {
			getCodeItemsAndParent(codeType.getParentCode(), parentCodeItems);
		}

		List<CodeItem> codeItems = codeItemDao.selectOfType(typeCode);
		if (codeItems != null) {
			parentCodeItems.addAll(codeItems);
		}
	}
}
