$(document).ready(function () {
  $.ajax({
    url: "/api/cycleStocks",
    method: "GET",
    dataType: "json",
    success: function (data) {
      populateRentCycleTable(data);
      populateReturnCycleTable(data);
      populateBrandDropdown(data);
    },
    error: function () {
      console.error("Failed to fetch data from the API.");
    },
  });
});

function populateRentCycleTable(data) {
  const rentCycleTable = $("#rentCycleTable tbody");
  rentCycleTable.empty();
  data.forEach(function (cycle) {
    if (cycle.availableCycles > 0) {
      const row = `
        <tr>
          <td>${cycle.id}</td>
          <td>${cycle.brand}</td>
          <td>${cycle.availableCycles}</td>
          <td><button class="checkin-button" onclick="borrowCycle(${cycle.id})">Borrow</button></td>
        </tr>
      `;
      rentCycleTable.append(row);
    }
  });
}

function populateReturnCycleTable(data) {
  const returnCycleTable = $("#returnCycleTable tbody");
  returnCycleTable.empty();
  data.forEach(function (cycle) {
    const row = `
      <tr>
        <td>${cycle.id}</td>
        <td>${cycle.brand}</td>
        <td><button class="checkout-button" onclick="returnCycle(${cycle.id})">Return</button></td>
      </tr>
    `;
    returnCycleTable.append(row);
  });
}

function populateBrandDropdown(data) {
  const brandDropdown = $("#branddropdown");
  brandDropdown.empty();
  data.forEach(function (cycle) {
    const option = `<option value="${cycle.id}">${cycle.brand}</option>`;
    brandDropdown.append(option);
  });
}

function borrowCycle(id) {
  console.log(id);
  $.ajax({
    url: "/api/cycleStocks/"+id+"/borrow",
    method: "POST",
    success: function (data) {
      $.ajax({
        url: "/api/cycleStocks",
        method: "GET",
        dataType: "json",
        success: function (data) {
          populateRentCycleTable(data);
          populateReturnCycleTable(data);
          populateBrandDropdown(data);
        },
        error: function () {
          console.error("Failed to fetch data from the API.");
        },
      });
    },
    error: function () {
      console.error("Failed to borrow a cycle.");
    },
  });
}

function returnCycle(id) {
  $.ajax({
    url: "/api/cycleStocks/"+id+"/return",
    method: "POST",
    success: function (data) {
      $.ajax({
        url: "/api/cycleStocks",
        method: "GET",
        dataType: "json",
        success: function (data) {
          populateRentCycleTable(data);
          populateReturnCycleTable(data);
          populateBrandDropdown(data);
        },
        error: function () {
          console.error("Failed to fetch data from the API.");
        },
      });
    },
    error: function () {
      console.error("Failed to return a cycle.");
    },
  });
}

$("#restockForm").submit(function (event) {
  event.preventDefault();
  const brandId = $("#branddropdown").val();
  const restockQuantity = $("#restock").val();
  $.ajax({
    url: "/api/cycleStocks/restock",
    method: "POST",
    contentType: "application/json",
    data: JSON.stringify({
      brandId: brandId,
      restockQuantity: restockQuantity,
    }),
    success: function (data) {
      $.ajax({
        url: "/api/cycleStocks",
        method: "GET",
        dataType: "json",
        success: function (data) {
          populateRentCycleTable(data);
          populateReturnCycleTable(data);
          populateBrandDropdown(data);
        },
        error: function () {
          console.error("Failed to fetch data from the API.");
        },
      });
    },
    error: function () {
      console.error("Failed to restock cycles.");
    },
  });
});
