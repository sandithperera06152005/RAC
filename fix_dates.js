const fs = require('fs');
const files = [
  'd:/GeoEdge_RAC/src/main/webapp/app/entities/autocarejob/update/autocarejob-instruction.component.ts',
  'd:/GeoEdge_RAC/src/main/webapp/app/entities/autocarejob/itemissue/autocarejob-itemissue.component.ts',
  'd:/GeoEdge_RAC/src/main/webapp/app/entities/receipt-modal/receipt-modal.component.ts',
  'd:/GeoEdge_RAC/src/main/webapp/app/entities/sales-invoice-lines/update/sales-invoice-lines-update.component.ts',
  'd:/GeoEdge_RAC/src/main/webapp/app/entities/autocareappointment/update/autocareappointment-update.component.ts',
];

files.forEach(file => {
  if (fs.existsSync(file)) {
    let content = fs.readFileSync(file, 'utf8');

    // Replace assignments using dayjs()
    content = content.replace(/:\s*dayjs\(\)/g, ": dayjs().add(-new Date().getTimezoneOffset(), 'minute')");
    content = content.replace(/=\s*dayjs\(\)/g, "= dayjs().add(-new Date().getTimezoneOffset(), 'minute')");
    content = content.replace(/\?\s*dayjs\(\)/g, "? dayjs().add(-new Date().getTimezoneOffset(), 'minute')");

    // Replace dayjs(item.lmd)
    content = content.replace(/dayjs\(item\.lmd\)/g, "dayjs(item.lmd).add(-new Date().getTimezoneOffset(), 'minute')");

    // Replace dayjs(existingHeader.addeddate)
    content = content.replace(
      /dayjs\(existingHeader\.addeddate\)/g,
      "dayjs(existingHeader.addeddate).add(-new Date().getTimezoneOffset(), 'minute')",
    );

    fs.writeFileSync(file, content, 'utf8');
    console.log('Processed: ' + file);
  }
});
