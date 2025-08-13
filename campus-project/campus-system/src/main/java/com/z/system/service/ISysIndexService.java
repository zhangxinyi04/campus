package com.z.system.service;


import com.z.common.core.domain.AjaxResult;

public interface ISysIndexService
{
    AjaxResult index();

    AjaxResult visualization(String semesterId);
}
