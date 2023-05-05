// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract PatientPortal {
    /*
     * NOTE:
     * Only the hospital (identified as owner) can call the contract's methods
     * 
     */
    address owner;
    mapping (string => string) private records;

    // if the corresponding application were able to interact with the contract:
    event Access(string key);
    event Remove(string key);
    event Add(string key, string value);
    event Edit(string key, string oldValue, string newValue);

    constructor() {
        owner = payable(msg.sender);
    }


    // modifier to check if caller is owner
    modifier isOwner() {
        require(msg.sender == owner, "Caller is not owner");
        _;
    }


    /*
     * returns the encrypted data that 'key' maps to
     */
    function accessRecord(string memory key) public view isOwner returns(string memory) {

        require(bytes(key).length == 44, "Incorrect Key Format");
        // emit Access(key);
        if (!containsRecord(key)) return "No Record Exists";
        return records[key];
    }


    /*
     * adds the record {key, value} to the mapping
     * returns true if the record was successfully added and reverts otherwise
     */
    function addRecord(string memory key, string memory value) public isOwner returns(bool) {

        require(bytes(key).length == 44, "Incorrect Key Format");
        require(bytes(value).length >= 172, "Incorrect Value Format");
        records[key] = value;
        emit Add(key, value);
        return true;
    }


    /*
     * removes the record associated with 'key' from the hospital records
     * returns true if the removal was successful and false otherwise
     */ 
    function removeRecord(string memory key) public isOwner returns(bool) {

        require(bytes(key).length == 44, "Incorrect Key Format");
        if (!containsRecord(key)) return false;
        delete records[key];
        emit Remove(key);
        return true;
    }


    /*
     * changes an existing record identified by 'key' to now hold 'newValue'
     * returns true if the change was successful and false otherwise
     */ 
    function editRecord(string memory key, string memory newValue) public isOwner returns(bool) {

        require(bytes(key).length == 44, "Incorrect Key Format");
        require(bytes(newValue).length >= 172, "Incorrect Value Format");

        if (!containsRecord(key)) return false;
        string memory oldValue = records[key]; // save to send to event
        records[key] = newValue;
        emit Edit(key, oldValue, newValue);
        return true;
    }


    /*
     * returns true if there is a record associated with 'key' and false otherwise
     */ 
    function containsRecord(string memory key) public view isOwner returns(bool) {
        return abi.encodePacked(records[key]).length > 0 ? true : false;
    }


    /*
     * close the contract
     */
    function close() public isOwner {
        // ????????????????????????????????????????????????????????????????????????????????????????????
        payable(owner).transfer(address(this).balance); // incase anything was sent for whatever reason
    }


}