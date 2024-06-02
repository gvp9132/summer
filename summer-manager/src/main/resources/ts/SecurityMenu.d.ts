/** 菜单信息数据类型声明 */
export type SecurityMenu = {
    /** 自增主键 */
    id: number;
    /** 数据唯一标识 */
    key: string;
    /** 菜单的上一级菜单ID */
    parentId: number;
    /** 菜单标签(名字) */
    label: string;
    /** 菜单功能描述 */
    describe: string;
    /** 菜单路径地址 */
    path: string;
    /** 菜单图标 */
    icon: string;
    /** 菜单是否是最后一级菜单 */
    last: boolean;
    /** 菜单排序字段 */
    menuOrder: number;
    /** 是否要显示菜单标志 */
    show: boolean;
    /** 创建时间 */
    createTime: Date;
    /** 修改时间 */
    updateTime: Date;
    /** 逻辑删除字段 */
    delete: boolean;
    /** 数据版本 */
    version: number;
    /** 数据排序字段 */
    order: number;
    /** 数据备注信息 */
    remark: string;
}
export default securityMenuColumns;
