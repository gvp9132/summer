import { createSlice, PayloadAction} from '@reduxjs/toolkit';

import { StatePagingType } from 'storage/redux';
import RequestStatus from 'storage/redux/RequestStatus';
import {builderPagingHandler} from "storage/redux/reducer";
import {fetchAddSecurityRole, fetchDeleteSecurityRole, fetchUpdateSecurityRole, fetchSecurityRolePageData} 
    from "storage/redux/async-action/securityRoleAsyncAction";
import UserStorage from "storage/local/UserStorage";

import {SecurityRole} from "type/entity/SecurityRoleUser";

/**
 * 角色信息表状态管理类型
 */
export type SecurityRoleState = StatePagingType<SecurityRole>

/** 状态初始化数据 */
const initialState: SecurityRole = {
    data: [],
    loading: false,
    requestStatus: RequestStatus.INIT,
    currentPage: 1,
    pageSize: UserStorage.getInstance().getAdminPageConfigFromLocalStore().securityRolePageSize,
    total: 0,
    totalPage: 10
};

export const securityRoleSlice = createSlice({
    name: 'securityRole',
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builderPagingHandler(builder, fetchSecurityRolePageData);
        /** 添加角色信息表信息 */
        builder.addCase(fetchAddSecurityRole.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 修改角色信息表信息 */
        builder.addCase(fetchUpdateSecurityRole.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 角色信息表用户信息 */
        builder.addCase(fetchDeleteSecurityRole.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
    }
});

export const {} = securityRoleSlice.actions;
export default securityRoleSlice.reducer;

