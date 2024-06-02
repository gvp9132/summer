import { createAsyncThunk } from "@reduxjs/toolkit";
import Request from "http/Request";
import {PagingData, PagingResult} from "type/Page";
import {SecurityUserRole} from "type/entity/SecurityUserRole";

/** 请求用户角色关系表分页数据 */
export const fetchSecurityUserRolePageData = createAsyncThunk(
    'securityUserRole/fetchSecurityUserRolePageData',
    async (paging: PagingData) => {
        return await Request.get<PagingResult<SecurityUserRole>>("/manager/admin/security_user_role/page/" + paging.page + "/" + paging.pageSize);
    }
);

/** 添加用户角色关系表数据 */
export const fetchAddSecurityUserRole = createAsyncThunk(
    'securityUserRole/fetchAddSecurityUserRole',
    async (vo: SecurityUserRole) => {
       return await Request.put<number>("/manager/admin/security_user_role", vo);
    }
);

/** 更新用户角色关系表数据 */
export const fetchUpdateSecurityUserRole = createAsyncThunk(
    'securityUserRole/fetchUpdateSecurityUserRole',
    async (vo: SecurityUserRole) => {
        return await Request.post<number>("/manager/admin/security_user_role", vo);
    }
);

/** 删除用户角色关系表数据 */
export const fetchDeleteSecurityUserRole = createAsyncThunk(
    'securityUserRole/fetchDeleteSecurityUserRole',
    async (id: number) => {
        return await Request.delete<number>("/manager/admin/security_user_role" + id);
    }
);

