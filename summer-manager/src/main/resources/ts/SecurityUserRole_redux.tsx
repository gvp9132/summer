import { createSlice, PayloadAction} from '@reduxjs/toolkit';

import { StatePagingType } from 'storage/redux';
import RequestStatus from 'storage/redux/RequestStatus';
import {builderPagingHandler} from "storage/redux/reducer";
import {fetchAddSecurityUserRole, fetchDeleteSecurityUserRole, fetchUpdateSecurityUserRole, fetchSecurityUserRolePageData} 
    from "storage/redux/async-action/securityUserRoleAsyncAction";
import UserStorage from "storage/local/UserStorage";

import {SecurityUserRole} from "type/entity/SecurityUserRoleUser";

/**
 * 用户角色关系表状态管理类型
 */
export type SecurityUserRoleState = StatePagingType<SecurityUserRole>

/** 状态初始化数据 */
const initialState: SecurityUserRole = {
    data: [],
    loading: false,
    requestStatus: RequestStatus.INIT,
    currentPage: 1,
    pageSize: UserStorage.getInstance().getAdminPageConfigFromLocalStore().securityUserRolePageSize,
    total: 0,
    totalPage: 10
};

export const securityUserRoleSlice = createSlice({
    name: 'securityUserRole',
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builderPagingHandler(builder, fetchSecurityUserRolePageData);
        /** 添加用户角色关系表信息 */
        builder.addCase(fetchAddSecurityUserRole.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 修改用户角色关系表信息 */
        builder.addCase(fetchUpdateSecurityUserRole.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 用户角色关系表用户信息 */
        builder.addCase(fetchDeleteSecurityUserRole.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
    }
});

export const {} = securityUserRoleSlice.actions;
export default securityUserRoleSlice.reducer;

