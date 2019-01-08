function myFunction() {
  // open the active sheet and clear it
  var spreadsheet = SpreadsheetApp.getActive(); 
  var groupsSheet = spreadsheet.getSheetByName('Groups');
  var oldGroupSheet = spreadsheet.getSheetByName('Old Groups');
  groupsSheet.clear();
  
  // set the headers
  var headers = [
    'Group Email'];
  groupsSheet.getRange('A1').setValues([headers]).setFontWeight('bold').set;
  groupsSheet.setFrozenRows(1);
  //groupsSheet.appendRow(["Test_Name", "Test Email", "x", "x"]);
  var oldGroupValues = oldGroupSheet.getRange(1, 1, oldGroupSheet.getLastRow()).getValues();
  var auditingValues = oldGroupSheet.getRange(2, 2, oldGroupSheet.getLastRow()).getValues();
  oldGroupValues = [].concat.apply([], oldGroupValues);
  auditingValues = [].concat.apply([], auditingValues);
  var notFound = false;
  for (var aV = 0; aV < auditingValues.length; aV++){
    Logger.log(auditingValues[aV]);
    var indexFound = auditingValues.indexOf(oldGroupValues) //looks at the oldValues to for the values in the auditing result  
       Logger.log(auditingValues[aV]);
      if (oldGroupValues[oG] == auditingValues[aV]){
        Logger.log("Found a match between oldGroupValues: " + oldGroupValues[oG] + " and auditingValues: " + auditingValues[aV]) 
        notFound = true;
        break;
      }
    }
    if (notFound == false) {
      groupsSheet.appendRow([oG]);
      Logger.log(oG);
    }
  }
  Logger.getLog();
}
